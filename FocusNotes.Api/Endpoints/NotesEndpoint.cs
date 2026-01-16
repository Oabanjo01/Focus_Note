using System;

namespace FocusNotes.Api.Endpoints;

public static class NotesEndpoint
{
    const string BaseEndpoint = "/Notes";
    const string EndpointName = "GetNotes";

    public static void MapNotesEndpoints(this WebApplication webApplication)
    {
        var Notesgroup = webApplication.MapGroup(BaseEndpoint);
    }
}
