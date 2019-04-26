package com.josec.demoh;

public class Cliente {
    String nombre;
    String apellido;
    String edad;
    String fechaNacimiento;

    public Cliente() {}

    public Cliente(String nombre, String apellido, String edad, String fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
    }
}
