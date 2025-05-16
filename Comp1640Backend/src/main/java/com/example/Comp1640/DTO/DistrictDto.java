package com.example.Comp1640.DTO;

public class DistrictDto {

    private Long id;

    private String name;

    public DistrictDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DistrictDto( String name) {
        this.name = name;
    }

    public DistrictDto() {}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
