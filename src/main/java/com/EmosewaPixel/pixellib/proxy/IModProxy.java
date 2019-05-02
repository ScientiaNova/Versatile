package com.EmosewaPixel.pixellib.proxy;

import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

public interface IModProxy {
    void enque(InterModEnqueueEvent e);

    void process(InterModProcessEvent e);
}
