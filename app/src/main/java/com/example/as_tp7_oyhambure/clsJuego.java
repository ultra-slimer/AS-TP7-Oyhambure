package com.example.as_tp7_oyhambure;
import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

public class clsJuego {
    CCGLSurfaceView _VistaDelJuego;
    CCSize _Pantalla;
    Sprite _Jugador;
    Sprite _Alt;
    Label _TituloJuego;
    Sprite _Enemigo;
    Boolean _estaTocandoAlJugador1 =false;
    Boolean _estaTocandoAlAlt =false;
    int _ultimojugadortocado;
    CCPoint _puntoInicio;
    CCPoint _puntoFinal;
    long _horaInicio;
    long _horaFinal;

    public clsJuego(CCGLSurfaceView VistaDelJuego){
        _VistaDelJuego = VistaDelJuego;
    }

    public void ComenzarJuego() {
        Director.sharedDirector().attachInView(_VistaDelJuego);
        _Pantalla=Director.sharedDirector().displaySize();
        Scene escenaAUsar;
        escenaAUsar=EscenaComienzo();
        Director.sharedDirector().runWithScene(escenaAUsar);
    }

    private Scene EscenaComienzo(){
        Scene escenaADevolver;
        escenaADevolver=Scene.node();
        capaJuego unacapa;
        unacapa = new capaJuego();
        escenaADevolver.addChild(unacapa);
        return escenaADevolver;
    }

    class capaJuego extends Layer {
        public capaJuego(){
            setIsTouchEnabled(true);
            ponerJugador();
            ponerAlt();
            ponerFondo();
            super.schedule("detectarColisiones", 0.10f);
        }

        void ponerFondo(){
            Sprite imagenfondo;
            imagenfondo=Sprite.sprite("fondito.jpg");
            imagenfondo.setPosition(_Pantalla.getWidth()/2,_Pantalla.getHeight()/2);
            float factorAncho, factorAlto;
            factorAncho=_Pantalla.getWidth()/imagenfondo.getWidth();
            factorAlto=_Pantalla.getHeight()/imagenfondo.getHeight();
            imagenfondo.runAction(ScaleBy.action(0.01f,factorAncho,factorAlto));
            super.addChild(imagenfondo,-10);
        }

        void ponerJugador(){
            _Jugador=Sprite.sprite("xwing.png");
            CCPoint posicionInicial;
            posicionInicial = new CCPoint();
            Random generadorDeAzar= new Random();
            Random generadorDeAzarY = new Random();
            float anchoEnemigo, alturaEnemigo;
            anchoEnemigo = _Jugador.getWidth();
            alturaEnemigo = _Jugador.getHeight();
            posicionInicial.x = generadorDeAzar.nextInt((int) (_Pantalla.getWidth() - anchoEnemigo));
            posicionInicial.x += anchoEnemigo/2;
            posicionInicial.y = generadorDeAzarY.nextInt((int) (_Pantalla.getHeight() - alturaEnemigo));
            posicionInicial.y += alturaEnemigo/2;
            _Jugador.setPosition(posicionInicial.x,posicionInicial.y);
            super.addChild(_Jugador,10);
        }

        void ponerAlt(){
            _Alt =Sprite.sprite("ywing.png");
            CCPoint posicionInicial;
            posicionInicial = new CCPoint();
            Random generadorDeAzar= new Random();
            Random generadorDeAzarY = new Random();
            float anchoEnemigo, alturaEnemigo;
            anchoEnemigo = _Alt.getWidth();
            alturaEnemigo = _Alt.getHeight();
            do {
                posicionInicial.x = generadorDeAzar.nextInt((int) (_Pantalla.getWidth() - anchoEnemigo));
                posicionInicial.x += anchoEnemigo/2;
                posicionInicial.y = generadorDeAzarY.nextInt((int) (_Pantalla.getHeight() - alturaEnemigo));
                posicionInicial.y += alturaEnemigo/2;
                _Alt.setPosition(posicionInicial.x,posicionInicial.y);
            } while(InterseccionEntreSprites(_Jugador, _Alt));
            super.addChild(_Alt,9);
        }

        void ponertitulo(){
            _TituloJuego.setPosition(_Pantalla.getWidth()/2, _Pantalla.getHeight() - _TituloJuego.getHeight()/2);
            CCColor3B colortitulo;
            colortitulo = new CCColor3B(255,130,255);
            _TituloJuego.setColor(colortitulo);
            super.addChild(_TituloJuego, -5);
        }

        public boolean InterseccionEntreSprites(Sprite Sprite1, Sprite Sprite2){
            Boolean HayInterseccion=false;

            Float Sp1Arriba, Sp1Abajo, Sp1Derecha, Sp1Izquierda, Sp2Arriba, Sp2Abajo, Sp2Derecha, Sp2Izquierda;

            Sp1Arriba=Sprite1.getPositionY() + Sprite1.getHeight()/2;
            Sp1Abajo = Sprite1.getPositionY() - Sprite1.getHeight()/2;
            Sp1Derecha=Sprite1.getPositionX() + Sprite1.getWidth()/2;
            Sp1Izquierda=Sprite1.getPositionX() - Sprite1.getWidth()/2;
            Sp2Arriba=Sprite2.getPositionY() + Sprite2.getHeight()/2;
            Sp2Abajo=Sprite2.getPositionY() - Sprite2.getHeight()/2;
            Sp2Derecha=Sprite2.getPositionX() + Sprite2.getWidth()/2;
            Sp2Izquierda=Sprite2.getPositionX() - Sprite2.getWidth()/2;

            if (Sp1Arriba>=Sp2Abajo && Sp1Arriba<=Sp2Arriba && Sp1Derecha>=Sp2Izquierda && Sp1Derecha<=Sp2Derecha) {
                HayInterseccion=true;
            }
            if (Sp1Arriba>=Sp2Abajo && Sp1Arriba<=Sp2Arriba &&
                    Sp1Izquierda>=Sp2Izquierda && Sp1Izquierda<=Sp2Derecha) {
                HayInterseccion=true;
            }
            if (Sp1Abajo>=Sp2Abajo && Sp1Abajo<=Sp2Arriba &&
                    Sp1Derecha>=Sp2Izquierda && Sp1Derecha<=Sp2Derecha) {
                HayInterseccion=true;
            }

            if (Sp1Abajo>=Sp2Abajo && Sp1Abajo<=Sp2Arriba &&
                    Sp1Izquierda>=Sp2Izquierda && Sp1Izquierda<=Sp2Derecha) {
                HayInterseccion=true;
            }
            if (Sp2Arriba>=Sp1Abajo && Sp2Arriba<=Sp1Arriba &&
                    Sp2Derecha>=Sp1Izquierda && Sp2Derecha<=Sp1Derecha) {
                HayInterseccion=true;
            }
            if (Sp2Arriba>=Sp1Abajo && Sp2Arriba<=Sp1Arriba &&
                    Sp2Izquierda>=Sp1Izquierda && Sp2Izquierda<=Sp1Derecha) {
                HayInterseccion=true;
            }
            if (Sp2Abajo>=Sp1Abajo && Sp2Abajo<=Sp1Arriba &&
                    Sp2Derecha>=Sp1Izquierda && Sp2Derecha<=Sp1Derecha) {
                HayInterseccion=true;
            }
            if (Sp2Abajo>=Sp1Abajo && Sp2Abajo<=Sp1Arriba &&
                    Sp2Izquierda>=Sp1Izquierda && Sp2Izquierda<=Sp1Derecha) {
                HayInterseccion=true;
            }
            if(HayInterseccion){
                MoveBy moversearriba, moverseizquierda, moverseabajo, moverderecha;
                IntervalAction secuenciaCuadrado;
                moversearriba = MoveBy.action(1,0,300);
                moverseizquierda = MoveBy.action(1,-300,0);
                moverseabajo = MoveBy.action(1,0,-300);
                moverderecha = MoveBy.action(1,300,0);
                secuenciaCuadrado= Sequence.actions(moversearriba, moverseizquierda, moverseabajo, moverderecha);
                if(_estaTocandoAlJugador1){
                    _Alt.runAction(secuenciaCuadrado);
                }
                else if (_estaTocandoAlAlt){
                    _Jugador.runAction(secuenciaCuadrado);
                }
            }
            return HayInterseccion;
        }

        public void detectarColisiones(float deltaTiempo){
            boolean huboColision;
            huboColision = false;
            if(InterseccionEntreSprites(_Jugador, _Alt))
            {
                huboColision = true;
            }

            if (huboColision){
                Log.d("Colision", "si");
            }
            if(!huboColision){
                Log.d("Colision", "no");
            }
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event){

            float xTocada, yTocada;
            xTocada = event.getX();
            yTocada= _Pantalla.getHeight()-event.getY();
            _puntoInicio = new CCPoint();
            _puntoInicio.x = xTocada;
            _puntoInicio.y = yTocada;
            _horaInicio = System.currentTimeMillis();
            float cerca1x, cerca1y, cerca2x, cerca2y, total1,total2;
            if(InterseccionEntrePuntoySprite(_Jugador,xTocada,yTocada)){
                moverNaveJugador(_Jugador, xTocada,yTocada);
                _estaTocandoAlJugador1 = true;
            }
            else {
                _estaTocandoAlJugador1 = false;
                if(InterseccionEntrePuntoySprite(_Alt,xTocada,yTocada)){
                    moverNaveJugador(_Alt, xTocada,yTocada);
                    _estaTocandoAlAlt = true;
                }
                else{
                    _estaTocandoAlAlt = false;
                }
            }


            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event){
            float xTocada, yTocada;
            xTocada = event.getX();
            yTocada= _Pantalla.getHeight()-event.getY();
            if(_estaTocandoAlJugador1){
                moverNaveJugador(_Jugador, xTocada, yTocada);
            }
            if(_estaTocandoAlAlt){
                moverNaveJugador(_Alt, xTocada, yTocada);
            }
            return true;
        }
        @Override
        public boolean ccTouchesEnded(MotionEvent event){
            float xTocada, yTocada;
            xTocada = event.getX();
            yTocada= _Pantalla.getHeight()-event.getY();
            _estaTocandoAlJugador1 = false;
            _estaTocandoAlAlt = false;
            _puntoFinal = new CCPoint();
            _puntoFinal.x = xTocada;
            _puntoFinal.y = yTocada;
            _horaFinal = System.currentTimeMillis();
            long deltaMilisegundos;
            deltaMilisegundos = _horaFinal - _horaInicio;

            String direccionDesplazamiento;
            direccionDesplazamiento=determinarDireccionDesplazamiento(_puntoInicio, _puntoFinal);
            return true;
        }

        void moverNaveJugador(Sprite spriteAMover, float xAMover, float yAMover){
            spriteAMover.setPosition(xAMover, yAMover);
        }

        public boolean InterseccionEntrePuntoySprite(Sprite SpriteAVerificar, Float puntoXAVerificar, Float puntoYAVerificar) {
            Boolean HayInterseccion=false;
            //Determino los bordes de cada Sprite
            Float SpArriba, SpAbajo, SpDerecha, SpIzquierda;
            SpArriba=SpriteAVerificar.getPositionY() + SpriteAVerificar.getHeight()/2;
            SpAbajo=SpriteAVerificar.getPositionY() - SpriteAVerificar.getHeight()/2;
            SpDerecha=SpriteAVerificar.getPositionX() + SpriteAVerificar.getWidth()/2;
            SpIzquierda=SpriteAVerificar.getPositionX() - SpriteAVerificar.getWidth()/2;

            if (puntoXAVerificar>=SpIzquierda && puntoXAVerificar<=SpDerecha && puntoYAVerificar>=SpAbajo && puntoYAVerificar<=SpArriba) {
                HayInterseccion=true;
            }
            return HayInterseccion;
        }

        String determinarDireccionDesplazamiento(CCPoint puntoInicio, CCPoint puntoFinal) {
            String Devolver;
            if (puntoFinal.y > puntoInicio.y) {
                if (puntoFinal.x > puntoInicio.x) {
                    Devolver = "Arriba derecha";
                } else {
                    if (puntoFinal.x < puntoInicio.x) {
                        Devolver = "Arriba Izquierda";
                    } else {
                        Devolver = "Arriba exactamente";
                    }
                }
            } else {
                if (puntoFinal.y < puntoInicio.y) {
                    if (puntoFinal.x > puntoInicio.x) {
                        Devolver = "Abajo derecha";
                    } else {
                        if (puntoFinal.x < puntoInicio.x) {
                            Devolver = "Abajo Izquierda";
                        } else {
                            Devolver = "Abajo exactamente";
                        }
                    }
                } else {
                    if (puntoFinal.x > puntoInicio.x) {
                        Devolver = "Derecha exactamente";
                    } else {
                        if (puntoFinal.x < puntoInicio.x) {
                            Devolver = "Izquierda exactamente";
                        } else {
                            Devolver = "Sin desplazamiento";
                        }
                    }
                }
            }
            return Devolver;
        }



    }

}

