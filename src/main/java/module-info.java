module com.atlasfreight.response {
    requires transitive com.fasterxml.jackson.databind;
    requires transitive java.logging;

    // Exportar TODOS los paquetes que necesites
    exports com.atlasfreight.response;
    exports com.atlasfreight.response.error;
    exports com.atlasfreight.response.error.suberror;

    // Opens para reflexión de Jackson
    opens com.atlasfreight.response to com.fasterxml.jackson.databind;
    opens com.atlasfreight.response.error to com.fasterxml.jackson.databind;
    opens com.atlasfreight.response.error.suberror to com.fasterxml.jackson.databind;
}