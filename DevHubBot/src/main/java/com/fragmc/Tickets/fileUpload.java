package com.fragmc.Tickets;

import net.dv8tion.jda.api.utils.FileUpload;

import java.io.InputStream;

public class fileUpload extends FileUpload {
    public fileUpload(InputStream resource, String name) {
        super(resource, name);
    }
}
