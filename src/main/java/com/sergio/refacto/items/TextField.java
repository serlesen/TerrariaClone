package com.sergio.refacto.items;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TextField {

    int width, height;
    String text;
    Font font;
    BufferedImage image;

    public TextField(int width, String text) {
        this.width = width;
        this.height = 30;
        this.text = text;
        this.font = new Font("Chalkboard", Font.BOLD, 16);
        renderImage();
    }

    public void typeKey(char c) {
        text += c;
        renderImage();
    };

    public void deleteKey() {
        if (text.length() > 0) {
            text = text.substring(0, text.length()-1);
            renderImage();
        }
    }

    public void renderImage() {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = (Graphics2D) image.createGraphics();

        g2.setColor(Color.WHITE);
        g2.setFont(font);
        g2.drawString(text, 6, height-10);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, 3);
        g2.fillRect(0, 0, 3, height);
        g2.fillRect(0, height-3, width, 3);
        g2.fillRect(width-3, 0, 3, height);
    }
}
