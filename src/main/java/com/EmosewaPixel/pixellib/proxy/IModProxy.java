package com.EmosewaPixel.pixellib.proxy;

import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

//This is an interface used for making Mod Proxies
public interface IModProxy {
    void init();

    void enque(InterModEnqueueEvent e);

    void process(InterModProcessEvent e);
}
