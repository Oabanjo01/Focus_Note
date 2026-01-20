using FocusNotes.Api.Models.Entities;

namespace FocusNotes.Api.Models.Dtos;

public record ModifyNoteDto
{
    public int Id;
    public string? Name;
    public string? Content;
    public bool? IsCompleted;
    public NoteCategory Category;
    public bool? IsTodo;
}
