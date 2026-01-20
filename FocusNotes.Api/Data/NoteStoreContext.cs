using System;
using FocusNotes.Api.Models.Entities;
using Microsoft.EntityFrameworkCore;

namespace FocusNotes.Api.Data;

public class NoteStoreContext(DbContextOptions<NoteStoreContext> noteOptions) : DbContext(options: noteOptions)
{
    public DbSet<Notes> Notes => Set<Notes>();
    public DbSet<User> Users => Set<User>();
}
