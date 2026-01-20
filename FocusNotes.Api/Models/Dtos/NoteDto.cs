using System.ComponentModel.DataAnnotations;
using FocusNotes.Api.Models.Entities;

namespace FocusNotes.Api.Models.Dtos;

public record NoteDto(
    [Required][StringLength(50)] string Name,
    string Content,
    bool IsCompleted,
    bool IsTodo,
    NoteCategory Category,
    DateTime CreatedAt
)
{

}
