package ec.edu.espam.api.practical.util;

import org.modelmapper.ModelMapper;

public class Mapper {
    private Mapper() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot be instantiated");
    }
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

