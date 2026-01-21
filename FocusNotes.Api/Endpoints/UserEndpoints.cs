using System;
using FocusNotes.Api.Data;
using FocusNotes.Api.Models.Dtos.Notes;
using FocusNotes.Api.Models.Dtos.Users;
using FocusNotes.Api.Models.Entities;
using Microsoft.EntityFrameworkCore;

namespace FocusNotes.Api.Endpoints;

public static class UserEndpoints
{
    private const string BaseEndpoint = "/User";
    private const string GetUserById = "GetUser";

    public static void MapUserEndpoints(this WebApplication webApplication)
    {

        RouteGroupBuilder userGroup = webApplication.MapGroup(BaseEndpoint);

        userGroup.MapGet("/{id}", async (NoteStoreContext noteStoreContext, int id) =>
        {
            User? user = await noteStoreContext.Users.Include(user => user.Notes).FirstOrDefaultAsync(user => user.Id == id);

            if (user is null) return Results.NotFound();

            var noteDtos = user.Notes.Select(n => new FetchNoteDto(
            n.Id,
            n.Name,
            n.Content ?? "",
            n.IsCompleted,
            n.IsTodo,
            n.Category,
            n.CreatedAt)).ToList();

            var userDto = new UserDto(
        user.Id,
        user.Name,
        user.Nickname,
        noteDtos,
        user.CreatedAt
    );

            return Results.Ok(userDto);

        }).WithName(GetUserById);

        userGroup.MapPost("/", async (NoteStoreContext noteStoreContext, CreateUserDto newUser) =>
        {
            User note = new()
            {
                Name = newUser.Name,
                CreatedAt = DateTime.UtcNow,
                Nickname = newUser.Nickname,
            };

            noteStoreContext.Users.Add(note);

            await noteStoreContext.SaveChangesAsync();

            return Results.Ok();
        });
    }
}
