package networking.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import networking.rpcprotocol.Request;
import networking.rpcprotocol.RequestType;

public class JSONUtils {
    public static Request getRequest(String json){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        long type = jsonObject.get("Type").getAsLong();
        /*if (type == 1) {
            JsonObject data = jsonObject.get("Data").getAsJsonObject();
            String cnp = data.get("cnp").getAsString();
            String name = data.get("name").getAsString();
            String staff = data.get("type").getAsString();
            Person person = new Person(cnp,name,staff);
            return new Request.Builder().type(RequestType.LOGIN).data(person).build();
        }
        if (type == 2){
            JsonObject data = jsonObject.get("Data").getAsJsonObject();
            String cnp = data.get("cnp").getAsString();
            String name = data.get("name").getAsString();
            String staff = data.get("type").getAsString();
            Person person = new Person(cnp,name,staff);
            return new Request.Builder().type(RequestType.LOGOUT).data(person).build();
        }
        if (type == 4) {
            return new Request.Builder().type(RequestType.GET_ALL_RIDES).build();
        }
        //destination;
        //    private String ridesDate;
        //    private String ridesHour;
        //    private Integer numberOfSeats;
        if (type == 5) {
            JsonObject data = jsonObject.get("Data").getAsJsonObject();
            //int id = data.get("id").getAsInt();
            String destination = data.get("destination").getAsString();
            String ridesDate = data.get("ridesDate").getAsString();
            String ridesHour = data.get("ridesHour").getAsString();
            int numberOfSeats= data.get("numberOfSeats").getAsInt();
            Ride ride = new Ride(3,destination,ridesDate,ridesHour,numberOfSeats);
            return new Request.Builder().type(RequestType.GET_RIDE).data(ride).build();
        }
        if (type == 6) {
            JsonObject data = jsonObject.get("Data").getAsJsonObject();
            //int id = data.get("id").getAsInt();
            String destination = data.get("destination").getAsString();
            String ridesDate = data.get("ridesDate").getAsString();
            String ridesHour = data.get("ridesHour").getAsString();
            int numberOfSeats= data.get("numberOfSeats").getAsInt();
            Ride ride = new Ride(3,destination,ridesDate,ridesHour,numberOfSeats);
            return new Request.Builder().type(RequestType.GET_SEATS).data(ride).build();
        }
        if (type == 7) {
            JsonObject data = jsonObject.get("Data").getAsJsonObject();
            //int id = data.get("id").getAsInt();
            String cnp = data.get("cnp").getAsString();
            String name= data.get("name").getAsString();
            String typee = data.get("type").getAsString();
            Person person= new Person(cnp,name,typee);
            return new Request.Builder().type(RequestType.GET_PERSON).data(person).build();
        }
        if(type == 3){
            //{"Type":3,"Data":{"id":35,"ride":{"destination":"Bali","ridesDate":"2023-09-09","ridesHour":"12:00:00","numberOfSeats":3,"Id":3},"numberOfSeat":0,"person":{"cnp":"1","name":"Dume Alexandra","type":"sss","Id":"1"},"Id":35}}
            //
            JsonObject data = jsonObject.get("Data").getAsJsonObject();
            JsonObject rideJSON = data.get("ride").getAsJsonObject();
            JsonObject personJSON = data.get("person").getAsJsonObject();
            int id = data.get("id").getAsInt();
            String destination = rideJSON.get("destination").getAsString();
            String ridesDate = rideJSON.get("ridesDate").getAsString();
            String ridesHour = rideJSON.get("ridesHour").getAsString();
            int numberOfSeats= rideJSON.get("numberOfSeats").getAsInt();
            int idRide= rideJSON.get("Id").getAsInt();
            Ride ride = new Ride(idRide,destination,ridesDate,ridesHour,numberOfSeats);
            int numberOfSeat = data.get("numberOfSeat").getAsInt();
            String cnp = personJSON.get("cnp").getAsString();
            String name= personJSON.get("name").getAsString();
            String typee = personJSON.get("type").getAsString();
            Person person= new Person(cnp,name,typee);
            Seat seat = new Seat(id,ride,numberOfSeat,person);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(seat);
            return new Request.Builder().type(RequestType.UPDATE_SEATS).data(seat).build();


        }
        if (type == 8) {
            new Request.Builder().type(RequestType.GET_MINIM).build();
        }

        /*if (type == 2) {
            return new Request.Builder().type(RequestType.GET_ALL_RIDES).build();
        }
        /*if (type == 4) {
            String date = jsonObject.get("Data").getAsString();
            return new Request.Builder().type(RequestType.GET_ALL_SPECTACLES_BY_DATE).data(date).build();
        }
        if (type == 5) {
            JsonObject data = jsonObject.get("Data").getAsJsonObject();
            JsonObject spectacleJSON = data.get("Spectacle").getAsJsonObject();

            long id = spectacleJSON.get("Id").getAsLong();
            String artistName = spectacleJSON.get("ArtistName").getAsString();
            String location = spectacleJSON.get("Location").getAsString();
            String date = spectacleJSON.get("Date").getAsString();
            String startHour = spectacleJSON.get("StartHour").getAsString();
            int availableSeats = spectacleJSON.get("AvailableSeats").getAsInt();
            int soldSeats = spectacleJSON.get("SoldSeats").getAsInt();
            Spectacle spectacle = new Spectacle(id,artistName,date,location,availableSeats,soldSeats,startHour);
            int noOfSeats = data.get("NoOfSeats").getAsInt();
            SpectacleSeatsDTO spectacleSeatsDTO = new SpectacleSeatsDTO(spectacle,noOfSeats);
            return new Request.Builder().type(RequestType.UPDATE_SEATS).data(spectacleSeatsDTO).build();
        }*/
        return null;
    }
}
