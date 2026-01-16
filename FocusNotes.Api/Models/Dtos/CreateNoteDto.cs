using System.ComponentModel.DataAnnotations;

namespace FocusNotes.Api.Models.Dtos;

public record CreateNoteDto(
    [Required][StringLength(50)] string Name,
    [Range(1, 9)] int Content,
    bool IsCompleted,
    bool IsTodo,
    DateOnly Releasedate
)
{

}
