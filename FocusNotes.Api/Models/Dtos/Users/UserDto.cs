using System.ComponentModel.DataAnnotations;
using FocusNotes.Api.Models.Dtos.Notes;

namespace FocusNotes.Api.Models.Dtos.Users;

public record UserDto(
    int Id,
    [Required][StringLength(50, MinimumLength = 3)] string Name,
    [StringLength(50)] string? Nickname,
    ICollection<FetchNoteDto> Notes,
    DateTime CreatedAt
)
{

}
