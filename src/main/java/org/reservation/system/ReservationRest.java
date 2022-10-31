package org.reservation.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.rest.RestHandlerBuilder;
import lombok.Builder;
import org.jose4j.json.internal.json_simple.parser.JSONParser;

import javax.ws.rs.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationRest {



        public static void main(String[] args) {
            HotelBookingResource hotelBookingResource = new HotelBookingResource();
            MuServer server = MuServerBuilder.httpServer()
                    .addHandler(
                            RestHandlerBuilder.restHandler(hotelBookingResource)
                                    .addCustomWriter(new JacksonJaxbJsonProvider())
                                    .addCustomReader(new JacksonJaxbJsonProvider())
                    )
                    .start();

            System.out.println("API example: " + server.uri().resolve("/reservations"));
        }


        @Builder
        public static class Bookings {
            public boolean isAvailable;
            public String customerName;


            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @JsonSerialize(using = LocalDateTimeSerializer.class)
            @JsonDeserialize(using = LocalDateTimeDeserializer.class)
            private LocalDateTime startTime;

            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @JsonSerialize(using = LocalDateTimeSerializer.class)
            @JsonDeserialize(using = LocalDateTimeDeserializer.class)private LocalDateTime endTime;

            public int tableSize;
        }

        @Path("/reservations")
        static class HotelBookingResource {
            DateTimeFormatter formatter
                    = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            @GET
            @Produces("application/json")
            public Bookings getBookings() {
                return Bookings.builder().
                        startTime(LocalDateTime.now()).
                        endTime(LocalDateTime.now().plusHours(2)).
                        customerName("San Joe").
                        isAvailable(true).tableSize(4).
                        build();
            }

            @POST
            @Consumes("application/json")
            @Produces("text/plain")
            public String postBookings(Bookings bookings) {
                JSONParser jsonParser = new JSONParser();
                return "I got a user with isActive=" + bookings.isAvailable
                        + " and name " + bookings.customerName + " and start time is  " + bookings.startTime+
                        "end time is  " + bookings.endTime;
            }
        }

    }



