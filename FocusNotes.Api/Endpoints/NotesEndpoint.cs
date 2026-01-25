using System;
using FocusNotes.Api.Data;
using FocusNotes.Api.Models.Dtos;
using FocusNotes.Api.Models.Dtos.Notes;
using FocusNotes.Api.Models.Entities;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace FocusNotes.Api.Endpoints;

public static class NotesEndpoint
{
    const string BaseEndpoint = "/Notes";
    const string GetNotesById = "GetNotes";

    public static void MapNotesEndpoints(this WebApplication webApplication)
    {
        RouteGroupBuilder notesgroup = webApplication.MapGroup(BaseEndpoint);

        notesgroup.MapGet("/{userId:int}", async (
      int userId,
     NoteStoreContext noteStoreContext) =>
 {
     var fetchedNotes = await noteStoreContext.Notes
         .Where(note => note.UserId == userId)
         .Select(note => new FetchNoteDto(
             note.Id,
             note.Name,
             note.Content ?? "",
             note.IsCompleted,
             note.IsTodo,
             note.Category,
             note.CreatedAt))
         .AsNoTracking()
         .ToListAsync();

     return Results.Ok(fetchedNotes);
 });
        //  .Produces<List<FetchNoteDto>>(StatusCodes.Status200OK);

        notesgroup.MapGet("/{id:int}/{userId:int}", async (NoteStoreContext noteStoreContext, int id, int userId) =>
        {
            Notes? fetchedNotes = await noteStoreContext.Notes.Where(n => n.Id == id && n.UserId == userId)
            .FirstOrDefaultAsync();

            if (fetchedNotes is null) return Results.NotFound();

            FetchNoteDto noteById = new(fetchedNotes.Id, fetchedNotes.Name, fetchedNotes.Content ?? "", fetchedNotes.IsCompleted, fetchedNotes.IsTodo, fetchedNotes.Category, fetchedNotes.CreatedAt);

            return
                Results.Ok(noteById);
        }).WithName(GetNotesById).Produces<List<FetchNoteDto>>(StatusCodes.Status200OK)
.Produces(StatusCodes.Status404NotFound);

        notesgroup.MapPost("/", async (NoteStoreContext noteStoreContext, CreateNoteDto newNote) =>
        {
            Notes note = new()
            {
                Name = newNote.Name,
                Content = newNote.Content,
                CreatedAt = DateTime.UtcNow,
                IsCompleted = newNote.IsCompleted,
                IsTodo = newNote.IsTodo,
                Category = newNote.Category,
                UserId = newNote.UserId
            };

            noteStoreContext.Notes.Add(note);
            await noteStoreContext.SaveChangesAsync();

            FetchNoteDto fetchedNote = new(note.Id, note.Name, note.Content, note.IsCompleted, note.IsTodo, note.Category, note.CreatedAt);

            return Results.CreatedAtRoute(GetNotesById, new { id = fetchedNote.Id }, fetchedNote);
        });

        notesgroup.MapPut("/", async (int Id, NoteStoreContext noteStoreContext, ModifyNoteDto note) =>
        {
            Notes? noteToUpdate = await noteStoreContext.Notes.FindAsync(Id);

            if (noteToUpdate is null) return Results.NotFound();

            noteToUpdate.Content = note.Content;
            noteToUpdate.Name = note.Name ?? noteToUpdate.Name;
            noteToUpdate.IsCompleted = note.IsCompleted ?? noteToUpdate.IsCompleted;
            noteToUpdate.IsTodo = note.IsTodo ?? noteToUpdate.IsTodo;
            noteToUpdate.Category = note.Category;

            await noteStoreContext.SaveChangesAsync();


            return Results.NoContent();
        });

        notesgroup.MapDelete("/{id}", async (int id, NoteStoreContext noteStoreContext, int userId) =>
        {
            int noteToDelete = await noteStoreContext.Notes.Where(note => id == note.Id && note.UserId == userId).ExecuteDeleteAsync();

            return noteToDelete > 0 ? Results.NoContent() : Results.NotFound();
        });

    }
}
