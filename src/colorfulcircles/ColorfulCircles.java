/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package colorfulcircles;

import com.leapmotion.leap.*;
import java.io.IOException;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A sample that demonstrates how to draw and paint shapes, apply visual
 * effects, blend colors in overlapping objects, and animate objects.
 *
 * @see javafx.scene.effect.BlendMode
 * @see javafx.scene.effect.BoxBlur
 * @see javafx.scene.shape.Circle
 * @see javafx.scene.Group
 * @see javafx.scene.paint.LinearGradient
 * @see javafx.animation.Timeline
 */
public class ColorfulCircles extends Application {
    
//    private static final String AUDIO_URI = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
    private static final String AUDIO_URI = "file:///Users/tholapz/Downloads/a.mp3";
    private static MediaPlayer audioMediaPlayer;
    private static final double WIDTH = 1600, HEIGHT = 600;
    private static final double DIAMETER = 60;
    private static final double POS = 80;
    private Circle leap = new Circle(5, Color.FLORALWHITE);
    private Rectangle g1,g2,g3,g4,score;
    private Timeline animation;
    private Group root;
        LeapListener listener;
        Controller controller;
    private Scene scene;
    private Integer jk=0;
    private  Text text = new Text ("Score: 0");
    
    
       
    
    private void init(Stage primaryStage) {
        root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        
        Group layer1 = new Group();
        for(int i=0; i<1;i++) {
            Circle circle = new Circle(DIAMETER,Color.web("white",0.05f));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white",0.2f));
            circle.setStrokeWidth(2f);
            circle.setLayoutX(POS);
            
            layer1.getChildren().add(circle);
        }
        // create second list of circles
        Group layer2 = new Group();
        for(int i=0; i<20;i++) {
            Circle circle = new Circle(DIAMETER,Color.web("white",0.05f));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white",0.2f));
            circle.setStrokeWidth(2f);
            circle.setLayoutX(POS + WIDTH*1/4);
            layer2.getChildren().add(circle);
        }
        // create third list of circles
        Group layer3 = new Group();
        for(int i=0; i<10;i++) {
            Circle circle = new Circle(DIAMETER,Color.web("white",0.05f));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white",0.2f));
            circle.setStrokeWidth(2f);
            circle.setLayoutX(POS + WIDTH*2/4);
            layer3.getChildren().add(circle);
        }
        
        Group layer4 = new Group();
        for(int i = 0; i < 20; i++) {
            Circle circle = new Circle(DIAMETER, Color.web("white", 0.05f));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.2f));
            circle.setStrokeWidth(2f);
            circle.setLayoutX(POS + WIDTH*3/4);
            layer4.getChildren().add(circle);
            
        }
        // Set a blur effect on each layer
//        BoxBlur bb = new BoxBlur(30,30,3);
//        layer1.setEffect(bb);
//        layer2.setEffect(bb);
//        layer3.setEffect(bb);
//        layer4.setEffect(bb);
        // create a rectangle size of window with colored gradient
        Rectangle colors = new Rectangle(WIDTH, HEIGHT,
                new LinearGradient(0f,1f,1f,0f,true, CycleMethod.NO_CYCLE, new Stop(0,Color.web("#f8bd55")),
                        new Stop(0.14f,Color.web("#c0fe56")),
                        new Stop(0.28f,Color.web("#5dfbc1")),
                        new Stop(0.43f,Color.web("#64c2f8")),
                        new Stop(0.57f,Color.web("#be4af7")),
                        new Stop(0.71f,Color.web("#ed5fc2")),
                        new Stop(0.85f,Color.web("#ef504c")),
                        new Stop(1,Color.web("#f2660f")))
        );
        colors.setBlendMode(BlendMode.OVERLAY);
        g1 = new Rectangle(WIDTH/4, HEIGHT, Color.BLACK);
        g2 = new Rectangle(WIDTH/4, HEIGHT, Color.BLACK);
        g3 = new Rectangle(WIDTH/4, HEIGHT, Color.BLACK);
        g4 = new Rectangle(WIDTH/4, HEIGHT, Color.BLACK);
        
        g2.setLayoutX(WIDTH/4);
        g3.setLayoutX(WIDTH/2);
        g4.setLayoutX(3*WIDTH/4);
        
        score = new Rectangle(100, 50, Color.TRANSPARENT);
        
        
        StackPane stack = new StackPane();
        
        text.setFont(new Font(Font.getDefault().getFamily(), 30));
        text.setFill(Color.AQUA);
        stack.getChildren().addAll(score, text);
        // create main content
        Group group = new Group(
                g1,
                g2,
                g3,
                g4,
                layer1, 
                layer2,
                layer3,
                layer4,
                colors,
                stack
        );
        Rectangle clip = new Rectangle(WIDTH, HEIGHT);
        clip.setSmooth(false);
        group.setClip(clip);
        root.getChildren().add(group);
        // create list of all circles
        List<Node> allCircles = new ArrayList<Node>();
        allCircles.addAll(layer1.getChildren());
        allCircles.addAll(layer2.getChildren());
        allCircles.addAll(layer3.getChildren());
        allCircles.addAll(layer4.getChildren());
        // Create a animation to randomly move every circle in allCircles
        animation = new Timeline();
        Integer a = 0;
        for(Node circle: allCircles) {
            a++;
            animation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, // set start position at 0s
                    //new KeyValue(circle.translateXProperty(),random()*WIDTH),
                    new KeyValue(circle.translateYProperty(),HEIGHT*a*-1)
                ),
//                new KeyFrame(new Duration(2000 + 18*random()*1000),
//                    new KeyValue(circle.translateYProperty(), HEIGHT+DIAMETER)
//                )
                new KeyFrame(new Duration(2000*a), // set end position at 2s
                    //new KeyValue(circle.translateXProperty(),random()*WIDTH),
                    new KeyValue(circle.translateYProperty(),HEIGHT+DIAMETER)
                )
            );
        }
        //animation.setAutoReverse(true);
        //animation.setCycleCount(Animation.INDEFINITE);
    }

    
    
    
    @Override public void stop() {
        animation.stop();
        this.stopAudio();
        controller.removeListener(listener);
    }

    public void play() {
        animation.play();
        this.startAudio();
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
        play();
        
        listener = new LeapListener();
        
        controller = new Controller();
        
        controller.addListener(listener);
        leap.setLayoutX(leap.getRadius());
        leap.setLayoutY(leap.getRadius());
        root.getChildren().add(leap);
        listener.tapLocationProperty().addListener(new ChangeListener<Point2D>(){
            
            
            public void createLetter(String c) {
            final Text letter = new Text(c);
            letter.setFill(Color.PINK);
            letter.setFont(new Font(Font.getDefault().getFamily(), 60));
            letter.setTextOrigin(VPos.TOP);
            letter.setTranslateX((WIDTH - letter.getBoundsInLocal().getWidth()) / 2);
            letter.setTranslateY((HEIGHT - letter.getBoundsInLocal().getHeight()) / 2);
            root.getChildren().add(letter);
            // over 3 seconds move letter to random position and fade it out
            final Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent event) {
                            // we are done remove us from scene
                            root.getChildren().remove(letter);
                        }
                    }
       
            ));
            timeline.play();
        }

            @Override
            public void changed(ObservableValue ov, Point2D t, final Point2D t1) {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        Point2D d=root.sceneToLocal(t1.getX()-scene.getX()-scene.getWindow().getX(),
                                                    t1.getY()-scene.getY()-scene.getWindow().getY());
                        double dx=d.getX(), dy=d.getY();
                        
                        if(dx>=0d && dx<=WIDTH-2d*leap.getRadius() && 
                           dy>=0d && dy<=HEIGHT-2d*leap.getRadius()){
                            leap.setTranslateX(dx);
                            leap.setTranslateY(dy);                                
                        }
                        
                        
                        if(dx < WIDTH/4) {
                            System.out.println('1');
                            Timeline t = new Timeline();
                            t.getKeyFrames().addAll(
                            new KeyFrame(Duration.ZERO,
                                new KeyValue(g1.fillProperty(), Color.WHITE)
                            ),
                            new KeyFrame(new Duration(1000),
                                    new KeyValue(g1.fillProperty(), Color.BLACK)
                            )
                            );
                            jk+=123;
                            text.setText("Score "+jk.toString());
                            createLetter("EGG ROLL!");
                            t.play();
//                            if(n1.getCenterY() > HEIGHT/2) {
//                                System.out.println("hit!!!");
//                            } else {
//                                System.out.println("miss");
//                            }
                        }else if(dx < WIDTH/2) {
                            System.out.println('2');
                            Timeline t = new Timeline();
                            t.getKeyFrames().addAll(
                            new KeyFrame(Duration.ZERO,
                                new KeyValue(g2.fillProperty(), Color.WHITE)
                            ),
                            new KeyFrame(new Duration(1000),
                                    new KeyValue(g2.fillProperty(), Color.BLACK)
                            )
                            );
                            jk+=100;
                            text.setText("Score "+jk.toString());
                            createLetter("EGG ROLL!");
                            t.play();
                        }else if(dx < WIDTH*3/4) {
                            System.out.println('3');
                            Timeline t = new Timeline();
                            t.getKeyFrames().addAll(
                            new KeyFrame(Duration.ZERO,
                                new KeyValue(g3.fillProperty(), Color.WHITE)
                            ),
                            new KeyFrame(new Duration(1000),
                                    new KeyValue(g3.fillProperty(), Color.BLACK)
                            )
                            );
                            jk+=223;
                            text.setText("Score "+jk.toString());
                            createLetter("EGG ROLL!");
                            t.play();
                        }else {
                            System.out.println('4');
                            Timeline t = new Timeline();
                            t.getKeyFrames().addAll(
                            new KeyFrame(Duration.ZERO,
                                new KeyValue(g4.fillProperty(), Color.WHITE)
                            ),
                            new KeyFrame(new Duration(1000),
                                    new KeyValue(g4.fillProperty(), Color.BLACK)
                            )
                            );
                            jk+=323;
                            text.setText("Score "+jk.toString());
                            createLetter("EGG ROLL!");
                            t.play();
                        }
                    }
                });
            }
        
        });
//        listener.pointProperty().addListener(new ChangeListener<Point2D>(){
//            @Override
//            public void changed(ObservableValue ov, Point2D t, final Point2D t1) {
//                Platform.runLater(new Runnable(){
//                    @Override
//                    public void run() {
//                        Point2D d=root.sceneToLocal(t1.getX()-scene.getX()-scene.getWindow().getX(),
//                                                    t1.getY()-scene.getY()-scene.getWindow().getY());
//                        double dx=d.getX(), dy=d.getY();
//                        
//                        if(dx>=0d && dx<=WIDTH-2d*leap.getRadius() && 
//                           dy>=0d && dy<=HEIGHT-2d*leap.getRadius()){
//                            leap.setTranslateX(dx);
//                            leap.setTranslateY(dy);                                
//                        }
//                    }
//                });
//            }
//
//
//        });
                
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX 
     * application. main() serves only as fallback in case the 
     * application can not be launched through deployment artifacts,
     * e.g., in IDEs with limited FX support. NetBeans ignores main().
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void stopAudio() {
        getAudioMediaPlayer().pause();
    }

    private void startAudio() {
        getAudioMediaPlayer().play();
    }

    private static MediaPlayer getAudioMediaPlayer() {
        if(audioMediaPlayer == null) {
            Media audioMedia = new Media(AUDIO_URI);
            audioMediaPlayer = new MediaPlayer(audioMedia);
        }
        return audioMediaPlayer;
        
    }
}
