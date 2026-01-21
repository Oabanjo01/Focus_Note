using FocusNotes.Api.Models.Entities;

namespace FocusNotes.Api.Models.Dtos.Notes;

public record ModifyNoteDto
{
    public string? Name;
    public string? Content;
    public bool? IsCompleted;
    public NoteCategory Category;
    public bool? IsTodo;
}
