using System.ComponentModel.DataAnnotations;

namespace FocusNotes.Api.Models.Dtos.Users;

public record CreateUserDto(
    [Required][StringLength(50, MinimumLength = 3)] string Name,
    [StringLength(50)] string? Nickname,
    DateTime CreatedAt
)
{

}
