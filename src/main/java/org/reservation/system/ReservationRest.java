package org.reservation.system;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.openapi.OpenAPIObjectBuilder;
import io.muserver.rest.ApiResponse;
import io.muserver.rest.BasicAuthSecurityFilter;
import io.muserver.rest.Description;
import io.muserver.rest.RestHandlerBuilder;
import org.reservation.system.model.Bookings;
import org.reservation.system.util.Authorizer;
import org.reservation.system.util.UserPassAuthenticator;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static io.muserver.Mutils.htmlEncode;
import static io.muserver.openapi.ExternalDocumentationObjectBuilder.externalDocumentationObject;
import static io.muserver.openapi.InfoObjectBuilder.infoObject;
import static io.muserver.rest.CORSConfigBuilder.corsConfig;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;

public class ReservationRest {


        private static final Map<String, Map<String, List<String>>> usersToPasswordToRoles = new HashMap<>();



        public static void main(String[] args) {

            //to-do it should be configuration value
            usersToPasswordToRoles.put("Amit", singletonMap("s@curePa55word!", asList("User", "Admin")));
            usersToPasswordToRoles.put("Ram", singletonMap("password123", Collections.singletonList("User")));

            UserPassAuthenticator authenticator = new UserPassAuthenticator(usersToPasswordToRoles);
            Authorizer authorizer = new Authorizer();

            HotelBookingResource hotelBookingResource = new HotelBookingResource();
            MuServer server = MuServerBuilder.httpsServer()
                    .addHandler(
                            RestHandlerBuilder.restHandler(hotelBookingResource).addRequestFilter(new BasicAuthSecurityFilter("Basic-Auth", authenticator, authorizer))
                                    .addCustomWriter(new JacksonJaxbJsonProvider())
                                    .addCustomReader(new JacksonJaxbJsonProvider())
                    ).addHandler(Method.GET, "/", (request, response, pathParams) -> {
                    response.contentType("text/html");
                    response.write(getDemoPageHtml())
                    ;
                    }).addHandler(Method.POST, "/", (request, response, pathParams) -> {
                        response.contentType("text/html");
                        response.write(getDemoPageHtml())
                        ;
                    })
                    .addHandler(createRestHandler())
                    .start();

            System.out.println("API example: " + server.uri().resolve("/api.html"));
        }




        @Path("/reservations")
        static class HotelBookingResource {

            //required jdk 16 version
            List<Bookings> bookingsArrayList = Stream.of(Bookings.builder().bookingId("0").startTime(LocalDateTime.now()).contactNumber("1234567877")
                    .customerName("Fung Chu").restaurantName("HongKongVillage").status("CONFIRMED_BOOKING").endTime(LocalDateTime.now().plusHours(2)).tableSize(2)
                    .build()).toList();
            private static final AtomicLong idCounter = new AtomicLong();

            public static String createID()
            {
                return String.valueOf(idCounter.getAndIncrement());
            }

            @GET
            @Path("/getBookings")
            @Produces("application/json")
            @Description("Gets a single user")
            @ApiResponse(code = "200", message = "Success")
            @ApiResponse(code = "404", message = "No booking found")
            @HeaderParam(value = "userName")
            public List<Bookings> getBookings(@Context SecurityContext securityContext) {
                if (!securityContext.isUserInRole("Admin")) {
                    throw new ClientErrorException("This requires an Admin role", 403);
                }

                return new ArrayList<>(bookingsArrayList);
//                return Bookings.builder().
//                        startTime(LocalDateTime.now()).
//                        endTime(LocalDateTime.now().plusHours(2)).
//                        customerName("San Joe").
//                        isAvailable(true).tableSize(4).
//                        build();
            }

            @POST
            @Path("/addBooking")
            @Consumes("application/json")
            @Produces("text/plain")
            @Description("Creates a new user")
            @ApiResponse(code = "200", message = "Booking was created successfully")
            public String postBookings(@Context SecurityContext securityContext,Bookings bookings) {
                if (!securityContext.isUserInRole("User")) {
                    throw new ClientErrorException("This requires an Admin role", 403);
                }
                String bookingId = createID();
                // unique booking id creation
                bookings.setBookingId(bookingId);
                // two hours of booking slot is available
                bookings.setEndTime(bookings.getStartTime().plusHours(2));
                bookingsArrayList.add(bookings);

                return "I got a user with isActive=" + bookings.status
                        + " and name " + bookings.customerName;
            }
        }

    private static String getDemoPageHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Basic Auth Demo</title><style>th, td { padding: 20px; }</style></head><body><h1>Users</h1><table><thead><tr><th>Name</th><th>Password</th><th>Roles</th></tr></thead><tbody>");
        for (Map.Entry<String, Map<String, List<String>>> user : usersToPasswordToRoles.entrySet()) {
            html.append("<tr><td>").append(htmlEncode(user.getKey())).append("</td>");
            Map.Entry<String, List<String>> entry = user.getValue().entrySet().stream().findFirst().get();
            html.append("<td>").append(htmlEncode(entry.getKey())).append("</td><td>").append(htmlEncode(String.join(", ", entry.getValue()))).append("</td></tr>");
        }

        html.append("</tbody></table>");

        html.append("<h2>End points</h2><ul>" +
                "<li><a href=\"/things/read\">Read (requires User role)</a></li>" +
                "<li><a href=\"/things/admin\">Admin (requires Admin role)</a></li>" +
                "</ul>");

        html.append("</body></html>");
        return html.toString();
    }

    public static RestHandlerBuilder createRestHandler() {
        return RestHandlerBuilder.restHandler(new HotelBookingResource())
                .withCORS(corsConfig().withAllowedOriginRegex(".*"))
                .withOpenApiHtmlUrl("/api.html")
                .withOpenApiJsonUrl("/openapi.json")
                .withOpenApiDocument(
                        OpenAPIObjectBuilder.openAPIObject()
                                .withInfo(
                                        infoObject()
                                                .withTitle("Reservation API Documentation")
                                                .withDescription("Reservation system API documentation ")
                                                .withVersion("1.0")
                                                .build())
                                .withExternalDocs(
                                        externalDocumentationObject()
                                                .withDescription("Documentation docs")
                                                .withUrl(URI.create("https://muserver.io/jaxrs"))
                                                .build()
                                )
                );
    }

    }



