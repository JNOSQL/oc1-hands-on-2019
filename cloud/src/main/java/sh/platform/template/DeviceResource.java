package sh.platform.template;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

@Path("devices")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeviceResource {

    private static final Logger LOGGER = Logger.getLogger(DeviceResource.class.getName());

    @Inject
    private DevicePublisher publisher;

    @Inject
    private DeviceService devices;

    @POST
    public void insert(@Valid DeviceDTO dto) {
        LOGGER.info("new temperature " + dto);
        publisher.read(dto);
    }

    @GET
    public List<String> getDevices() {
        return devices.getDevices();
    }

    @GET
    @Path("status")
    public Iterable<Device> getStatus() {
        return devices.getStatus();
    }

}
