module networking {
    requires services;
    requires Domain;
    requires com.google.gson;

    exports networking.rpcprotocol;
    exports networking.utils;

}