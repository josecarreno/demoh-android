package com.josec.demoh;

import android.support.annotation.Keep;

@Keep
public class Cliente {
    public String nombre;
    public String apellido;
    public String edad;
    public String fechaNacimiento;

    public Cliente() {}

    public Cliente(String nombre, String apellido, String edad, String fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
    }
}
