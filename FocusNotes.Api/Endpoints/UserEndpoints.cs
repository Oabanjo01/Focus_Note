using System;
using FocusNotes.Api.Data;
using FocusNotes.Api.Models.Entities;
using Microsoft.EntityFrameworkCore;

namespace FocusNotes.Api.Endpoints;

public static class UserEndpoints
{
    private const string BaseEndpoint = "/User";
    private const string GetUserById = "GetUser";

    private readonly static List<User> users =
    [
    new User
    {
        Id = 1,
        Name = "Amina Bello",
        Nickname = "Ami",
        CreatedAt = DateTime.UtcNow.AddDays(-14)
    },
    new User
    {
        Id = 2,
        Name = "David Okafor",
        Nickname = "Dave",
        CreatedAt = DateTime.UtcNow.AddDays(-10)
    },
    new User
    {
        Id = 3,
        Name = "Lara Williams",
        Nickname = null,
        CreatedAt = DateTime.UtcNow.AddDays(-7)
    },
    new User
    {
        Id = 4,
        Name = "Samuel Johnson",
        Nickname = "SJ",
        CreatedAt = DateTime.UtcNow.AddDays(-3)
    },
    new User
    {
        Id = 5,
        Name = "Fatima Abdullahi",
        Nickname = "Fati",
        CreatedAt = DateTime.UtcNow
    }
];

    public static void MapUserEndpoints(this WebApplication webApplication)
    {

        RouteGroupBuilder userGroup = webApplication.MapGroup(BaseEndpoint);

        userGroup.MapGet("/{id}", async (NoteStoreContext noteStoreContext, int id) =>
        {
            await noteStoreContext.Users.Include(user => user.Notes).FirstOrDefaultAsync(user => user.Id == id);

        }).WithName(GetUserById);

        userGroup.MapPost("/", async (NoteStoreContext noteStoreContext) =>
        {
            await noteStoreContext.Users.AddRangeAsync(users);

            await noteStoreContext.SaveChangesAsync();

            return Results.Ok();
        });
    }
}
