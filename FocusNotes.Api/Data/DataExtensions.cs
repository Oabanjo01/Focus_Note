using System;
using Microsoft.EntityFrameworkCore;

namespace FocusNotes.Api.Data;

public static class DataExtensions
{
    public static void MigrateDB(this WebApplication builder)
    {
        using var scope = builder.Services.CreateScope();
        var dbContext = scope.ServiceProvider.GetRequiredService<NoteStoreContext>();
        dbContext.Database.Migrate();
    }

    public static void RegisterDbContext(this WebApplicationBuilder builder)
    {
        string? connectionString = builder.Configuration.GetConnectionString("NoteStoreContext");
        builder.Services.AddSqlite<NoteStoreContext>(connectionString);
    }
}
