using System;

namespace FocusNotes.Api.Models.Entities;

public class User
{
    public int Id { get; set; }
    public required string Name { get; set; }
    public string? Nickname { get; set; }

    public ICollection<Notes> Notes { get; set; } = [];

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
}
