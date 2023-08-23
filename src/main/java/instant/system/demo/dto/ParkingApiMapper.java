package instant.system.demo.dto;

import instant.system.demo.model.ParkingApi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ParkingApiMapper {
    ParkingApiMapper INSTANCE = Mappers.getMapper(ParkingApiMapper.class);

    @Mapping(source = "url", target = "parkingApi.url")
    @Mapping(source = "city", target = "parkingApi.city")
    @Mapping(source = "rootNode", target = "parkingApi.rootNode")
    ParkingApiDto toDto(ParkingApi parkingApi);

    ParkingApi toEntity(ParkingApiDto parkingApiDto);
}
