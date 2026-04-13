package br.uespi.phb.resource;

import br.uespi.phb.dto.CorResponse;
import br.uespi.phb.dto.Rgb;
import br.uespi.phb.service.ConversorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/converter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConversorResource {


    @Inject
    private ConversorService conversorService;

    @POST
    @Path("/rgb")
    public CorResponse converterAPartirDeRgb(Rgb rgb) {
        return conversorService.converterCoresAPartirDeRgb(rgb);
    }

}
