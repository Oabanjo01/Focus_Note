using System;
using FocusNotes.Api.Data;
using FocusNotes.Api.Models.Dtos;
using FocusNotes.Api.Models.Entities;
using Microsoft.EntityFrameworkCore;

namespace FocusNotes.Api.Endpoints;

public static class NotesEndpoint
{
    const string BaseEndpoint = "/Notes";
    const string GetNotesById = "GetNotes";

    public static void MapNotesEndpoints(this WebApplication webApplication)
    {
        RouteGroupBuilder notesgroup = webApplication.MapGroup(BaseEndpoint);

        notesgroup.MapGet("/", async (NoteStoreContext noteStoreContext) =>
        {
            List<FetchNoteDto> fetchedNotes = await noteStoreContext.Notes.Select(note => new FetchNoteDto(note.Id, note.Name, note.Content ?? "", note.IsCompleted, note.IsTodo, note.CreatedAt)).AsNoTracking().ToListAsync();

            return
                Results.Ok(fetchedNotes);
        });

        notesgroup.MapGet("/{id}/", async (NoteStoreContext noteStoreContext, int id) =>
        {
            Notes? fetchedNotes = await noteStoreContext.Notes.FindAsync(id);

            if (fetchedNotes is null) return Results.NotFound();

            FetchNoteDto noteById = new(fetchedNotes.Id, fetchedNotes.Name, fetchedNotes.Content ?? "", fetchedNotes.IsCompleted, fetchedNotes.IsTodo, fetchedNotes.CreatedAt);

            return
                Results.Ok(noteById);
        }).WithName(GetNotesById);

        notesgroup.MapPost("/", async (NoteStoreContext noteStoreContext, CreateNoteDto newNote) =>
        {
            Notes note = new()
            {
                Name = newNote.Name,
                Content = newNote.Content,
                CreatedAt = DateTime.UtcNow,
                IsCompleted = newNote.IsCompleted,
                IsTodo = newNote.IsTodo
            };

            await noteStoreContext.Notes.AddAsync(note);
            await noteStoreContext.SaveChangesAsync();

            FetchNoteDto fetchedNote = new(note.Id, note.Name, note.Content, note.IsCompleted, note.IsTodo, note.CreatedAt);

            return Results.CreatedAtRoute(GetNotesById, new { id = fetchedNote.Id }, fetchedNote);
        });
    }
}
