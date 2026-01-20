using System;

namespace FocusNotes.Api.Models.Entities;

public enum NoteCategory
{
    General,
    Work,
    Personal,
    Study,
    Urgent,
    None
}

public class Notes
{
    public int Id { set; get; }

    public User User { get; set; } = null!;
    public int UserId { get; set; }

    public required string Name { set; get; }
    public string? Content { set; get; }
    public bool IsCompleted { set; get; }
    public bool IsTodo { set; get; }
    public NoteCategory Category { set; get; }
    public DateTime CreatedAt { set; get; } = DateTime.UtcNow;
}
