package ru.covenant.code.landing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.covenant.code.landing.dto.request.ClientsDetailsRs;
import ru.covenant.code.landing.dto.request.ClientsRqDto;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.dto.response.ClientsListRsDto;
import ru.covenant.code.landing.dto.response.ClientsRsDto;
import ru.covenant.code.landing.entity.Clients;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientsMapper {

    @Mapping(source = "email", target = "email")
    @Mapping(source = "message", target = "message")
    @Mapping(constant = "NEW", target = "status")
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    Clients mapToClients(ClientsRqDto rq);

    ClientsRqDto mapToClientsRqDto(Clients clients);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "status")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    Clients mapToClients(ClientsRsDto clientsRsDto);

    ClientsRsDto mapToClientsRsDto(Clients clients);

    @Mapping(source = "status", target = "status")
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "name")
    @Mapping(ignore = true, target = "phone")
    @Mapping(ignore = true, target = "email")
    @Mapping(ignore = true, target = "message")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    Clients mapToClients(ClientsStatusRqDto clientsStatusRqDto);

    ClientsStatusRqDto mapToClientsStatusRqDto(Clients clients);

    ClientsAdminRsDto mapToClientsAdminRsDto(Clients clients);

    ClientsDetailsRs mapToClientsDetailsRs(Clients clients);
    List<ClientsListRsDto> mapToClientsListRsDto(List<Clients> clients);
}