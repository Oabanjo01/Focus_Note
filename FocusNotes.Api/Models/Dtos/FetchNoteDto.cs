using System.ComponentModel.DataAnnotations;

namespace FocusNotes.Api.Models.Dtos;

public record class FetchNoteDto(
    int Id,
    [Required][StringLength(50)] string Name,
    string Content,
    bool IsCompleted,
    bool IsTodo,
    DateTime CreatedAt
)
{

}
