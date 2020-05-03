import javax.vecmath.*;

import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class PokemonTrainer extends JFrame{
    public Canvas3D myCanvas3D;

    public PokemonTrainer(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);

        simpUniv.getViewingPlatform().setNominalViewingTransform();

        createSceneGraph(simpUniv);
        addLight(simpUniv);

        OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
        simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);

        setTitle("Tymchenkp, lab6, Pokemon Trainer");
        setSize(1280,720);
        getContentPane().add("Center", myCanvas3D);
        setVisible(true);
    }

    public void createSceneGraph(SimpleUniverse su){
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        BoundingSphere bs = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        String name;
        BranchGroup trainerBranchGroup = new BranchGroup();
        TextureLoader t = new TextureLoader("source_folder//pokemon.jpg", myCanvas3D);
        Background trainerBackground =  new Background(t.getImage());

        Scene trainerScene = null;
        try{
            trainerScene = f.load("source_folder//pokemon_trainer.obj");
        }
        catch (Exception e){
            System.out.println("File loading failed:" + e);
        }
        Hashtable roachNamedObjects = trainerScene.getNamedObjects();
        Enumeration enumer = roachNamedObjects.keys();
        while (enumer.hasMoreElements()){
            name = (String) enumer.nextElement();
            System.out.println("Name: " + name);
        }

        // start animation
        Transform3D startTransformation = new Transform3D();
        startTransformation.setScale(2.0/5);
        Transform3D combinedStartTransformation = new Transform3D();
        combinedStartTransformation.mul(startTransformation);

        TransformGroup scratStartTransformGroup = new TransformGroup(combinedStartTransformation);

        // moves
        int movesCount = 100; // moves count
        int movesDuration = 700; // moves for 0,3 seconds
        int startTime = 0; // launch animation after timeStart seconds

        // body
        Appearance bodyApp = new Appearance();
        setToMyDefaultAppearance(bodyApp, new Color3f(.0f, .0f, .0f));

        Alpha bodyRotAlpha = new Alpha(movesCount, Alpha.INCREASING_ENABLE, startTime, 0, movesDuration,0,0,0,0,0);

        Shape3D body = (Shape3D) roachNamedObjects.get("polygon0");
        body.setAppearance(bodyApp);
        TransformGroup bodyTG = new TransformGroup();
        bodyTG.addChild(body.cloneTree());

        Transform3D bodyRotAxis = new Transform3D();
        bodyRotAxis.set(new Vector3d(0.0, 0.0, 0.0));

        RotationInterpolator bodyRot = new RotationInterpolator(bodyRotAlpha, bodyTG, bodyRotAxis, 0.0f, (float) Math.PI*2);
        bodyRot.setSchedulingBounds(bs);
        bodyTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        bodyTG.addChild(bodyRot);

        // ball1
        Appearance ballApp = new Appearance();
        setToMyDefaultAppearance(ballApp, new Color3f(0.5f, 0.5f, 0.5f));

        Alpha ballAlpha = new Alpha(movesCount, Alpha.INCREASING_ENABLE, startTime, 0, movesDuration,0,0,0,0,0);

        Shape3D ball = (Shape3D) roachNamedObjects.get("ball2");
        ball.setAppearance(ballApp);
        TransformGroup ballTG = new TransformGroup();
        ballTG.addChild(ball.cloneTree());

        Transform3D ballRotAxis = new Transform3D();

        RotationInterpolator ballrot = new RotationInterpolator(ballAlpha, ballTG, ballRotAxis, 0.0f, (float) -Math.PI); // Math.PI*2
        ballrot.setSchedulingBounds(bs);
        ballTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        ballTG.addChild(ballrot);

        // ball2
        Appearance secondBallApp = new Appearance();
        setToMyDefaultAppearance(secondBallApp, new Color3f(0.2f, 0.2f, 0.2f));

        Alpha secondBallAlpha = new Alpha(movesCount, Alpha.INCREASING_ENABLE, startTime, 0, movesDuration,0,0,0,0,0);

        Shape3D secondBall = (Shape3D) roachNamedObjects.get("ball1");
        secondBall.setAppearance(secondBallApp);
        TransformGroup secondBallTG = new TransformGroup();
        secondBallTG.addChild(secondBall.cloneTree());

        Transform3D secondBallRotAxis = new Transform3D();

        RotationInterpolator secondBallrot = new RotationInterpolator(secondBallAlpha, secondBallTG, secondBallRotAxis, 0.0f, (float) Math.PI); // Math.PI*2
        secondBallrot.setSchedulingBounds(bs);
        secondBallTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        secondBallTG.addChild(secondBallrot);

        // head
        Appearance headApp = new Appearance();
        setToMyDefaultAppearance(headApp, new Color3f(0.25f, 0.23f, 0.29f));




        TransformGroup sceneGroup = new TransformGroup();
        sceneGroup.addChild(bodyTG);
        sceneGroup.addChild(ballTG);
        sceneGroup.addChild(secondBallTG);
        TransformGroup tgBody = new TransformGroup();
        Shape3D nShape = (Shape3D) roachNamedObjects.get("polygon1");
        nShape.setAppearance(headApp);
        tgBody.addChild(nShape.cloneTree());
        sceneGroup.addChild(tgBody.cloneTree());

        //face
        Appearance faceApp = new Appearance();
        setToMyDefaultAppearance(faceApp, new Color3f(0.6f, 0.5f, 0.35f));


        TransformGroup tgFace1 = new TransformGroup();
        Shape3D face1Shape = (Shape3D) roachNamedObjects.get("polygon3");
        face1Shape.setAppearance(faceApp);
        tgFace1.addChild(face1Shape.cloneTree());
        sceneGroup.addChild(tgFace1.cloneTree());

        TransformGroup tgFace2 = new TransformGroup();
        Shape3D face2Shape = (Shape3D) roachNamedObjects.get("polygon4");
        face2Shape.setAppearance(faceApp);
        tgFace2.addChild(face2Shape.cloneTree());
        sceneGroup.addChild(tgFace2.cloneTree());

        TransformGroup tgFace = new TransformGroup();
        Shape3D faceShape = (Shape3D) roachNamedObjects.get("polygon2");
        faceShape.setAppearance(faceApp);
        tgFace.addChild(faceShape.cloneTree());
        sceneGroup.addChild(tgFace.cloneTree());

        //bag
        Appearance bagApp = new Appearance();
        setToMyDefaultAppearance(bagApp, new Color3f(0.3f, 0.23f, 0.2f));

        TransformGroup tgBag1 = new TransformGroup();
        Shape3D bagShape1 = (Shape3D) roachNamedObjects.get("polygon6");
        bagShape1.setAppearance(bagApp);
        tgBag1.addChild(bagShape1.cloneTree());
        sceneGroup.addChild(tgBag1.cloneTree());

        TransformGroup tgBag = new TransformGroup();
        Shape3D bagShape = (Shape3D) roachNamedObjects.get("polygon7");
        bagShape.setAppearance(bagApp);
        tgBag.addChild(bagShape.cloneTree());
        sceneGroup.addChild(tgBag.cloneTree());


        TransformGroup whiteTransXformGroup = translate(
                scratStartTransformGroup,
                new Vector3f(0.0f,0.0f,-0.5f));

        TransformGroup whiteRotXformGroup = rotate(whiteTransXformGroup, new Alpha(10,5000));
        trainerBranchGroup.addChild(whiteRotXformGroup);
        scratStartTransformGroup.addChild(sceneGroup);

        BoundingSphere bounds = new BoundingSphere(new Point3d(120.0,250.0,100.0),Double.MAX_VALUE);
        trainerBackground.setApplicationBounds(bounds);
        trainerBranchGroup.addChild(trainerBackground);

        trainerBranchGroup.compile();
        su.addBranchGraph(trainerBranchGroup);
    }

    public void addLight(SimpleUniverse su){
        BranchGroup bgLight = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
        Vector3f lightDir1 = new Vector3f(-1.0f,0.0f,-0.5f);
        DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
        light1.setInfluencingBounds(bounds);
        bgLight.addChild(light1);
        su.addBranchGraph(bgLight);
    }

    private TransformGroup translate(Node node, Vector3f vector){

        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(vector);
        TransformGroup transformGroup =
                new TransformGroup();
        transformGroup.setTransform(transform3D);

        transformGroup.addChild(node);
        return transformGroup;
    }

    private TransformGroup rotate(Node node, Alpha alpha){
        TransformGroup xformGroup = new TransformGroup();
        xformGroup.setCapability(
                TransformGroup.ALLOW_TRANSFORM_WRITE);

        RotationInterpolator interpolator =
                new RotationInterpolator(alpha,xformGroup);

        interpolator.setSchedulingBounds(new BoundingSphere(
                new Point3d(0.0,0.0,0.0),1.0));

        xformGroup.addChild(interpolator);
        xformGroup.addChild(node);

        return xformGroup;
    }

    public static void setToMyDefaultAppearance(Appearance app, Color3f col) {
        app.setMaterial(new Material(col, col, col, col, 150.0f));
    }

    public static void main(String[] args) {
        PokemonTrainer start = new PokemonTrainer();
    }

}
