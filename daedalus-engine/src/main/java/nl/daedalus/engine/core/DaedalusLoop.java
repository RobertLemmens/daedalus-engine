package nl.daedalus.engine.core;

public interface DaedalusLoop {

    void onInit();
    void onUpdate(float dt);
    void onEvent();

}
