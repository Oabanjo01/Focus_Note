using System;

namespace FocusNotes.Api.Models.Entities;

public class Notes
{
    public int Id { set; get; }
    public required string Name { set; get; }
    public string? Content { set; get; }
    public bool IsCompleted { set; get; }
    public bool IsTodo { set; get; }
    public DateTime CreatedAt { set; get; } = DateTime.UtcNow;
}
