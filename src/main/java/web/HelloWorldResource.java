package web;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
 
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
 
@Path("helloworld")
public class HelloWorldResource {
	public static final String CLICHED_MESSAGE = "Hello World!";
 
	@GET
	@Path("/before")
	@Produces("text/plain")
	public String getHelloBefore() {
		return "hello before";
	}
 
	@GET
	@Produces("text/plain")
	public String getHello() {
		return CLICHED_MESSAGE;
	}
 
	@GET
	@Path("/{param}")
	@Produces("text/plain")
	public String getHello(@PathParam("param") String username) {
		return "Hello Path Param " + username;
	}
 
	@GET
	@Path("/user")
	@Produces("text/plain")
	public String getHelloWithQuery(@QueryParam("param") String username) {
		return "Hello Query Param " + username;
	}
 
	@GET
	@Path("{region:.+}/kaifeng/{district:\\w+}")
	public String getByAddress(
			@PathParam("region") final List<PathSegment> region,
			@PathParam("district") final String district) {
 
		final StringBuilder result = new StringBuilder();
		for (final PathSegment pathSegment : region) {
			result.append(pathSegment.getPath()).append("-");
		}
		result.append("kaifeng-" + district);
		return result.toString();
 
	}
 
	@GET
	@Path("query/{condition}")
	public String getByCondition(
			@PathParam("condition") final PathSegment condition) {
		StringBuilder conditions = new StringBuilder();
		final MultivaluedMap<String, String> matrixParameters = condition
				.getMatrixParameters();
 
		final Iterator<Entry<String, List<String>>> iterator = matrixParameters
				.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<String, List<String>> entry = iterator.next();
			conditions.append(entry.getKey()).append("=");
			conditions.append(entry.getValue()).append(" ");
		}
 
		return conditions.toString();
	}
 
	@GET
	@Path("weather/{condition}")
	public String getByCondition(
			@PathParam("condition") final PathSegment condition,
			@MatrixParam("program") final String program,
			@MatrixParam("type") final String type) {
 
		return condition.getPath() + " program = " + program + " type = "
				+ type;
	}
 
}

