package networking.rpcprotocol;



import java.io.Serializable;


public class Response implements Serializable {
    private ResponseType type;
    private Object data;

    private Response(){};

    public ResponseType type(){
        return type;
    }

    public Object data(){
        return data;
    }

    private void type(ResponseType type){
        this.type=type;
    }

    private void data(Object data){
        this.data=data;
    }

    @Override
    public String toString(){
        return "Response{" +
                "type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public String toJsonString() {
        return "";
    }


    public static class Builder{
        private Response response=new Response();

        public Builder type(ResponseType type) {
            response.type(type);
            return this;
        }

        public Builder data(Object data) {
            response.data(data);
            return this;
        }

        public Response build() {
            return response;
        }
    }

    /*public String toJsonString() {
        Gson gson = new Gson();
        System.out.println(data);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", String.valueOf(type));
        if (data instanceof Ride) {
            System.out.println("OBIECT");
            List<Ride> rides = new ArrayList<>();
            rides.add((Ride)data);
            String jsonArray = gson.toJson(rides);
            jsonObject.addProperty("data", jsonArray);
        } else if (data instanceof List) {
            System.out.println("LISTA");
            List<Ride> rides = (List<Ride>) data;
            String jsonArray = gson.toJson(rides);
            jsonObject.addProperty("data", jsonArray);
        }
        return jsonObject.toString();
    }*/


}
