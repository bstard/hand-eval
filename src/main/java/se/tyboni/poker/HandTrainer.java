package se.tyboni.poker;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.svm.KernelType;
import org.encog.ml.svm.SVM;
import org.encog.ml.svm.SVMType;
import org.encog.ml.svm.training.SVMTrain;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;

/**
 * Created with IntelliJ IDEA.
 * User: fredrik
 * Date: 10/8/12
 * Time: 8:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class HandTrainer {

    public static void main(String[] ugh) throws Exception {

        /*
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, false, 1));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 4));
        network.addLayer(new BasicLayer(new ActivationLinear(), true, 1));
        network.getStructure().finalizeStructure();
        network.reset();
        */
        SVM network = new SVM(1, SVMType.NewSupportVectorClassification, KernelType.Precomputed);
        int readCount = 10000;
        int startIndex = 0;

        double[][] inputArrays = new double[readCount][1];
        double[][] idealArrays = new double[readCount][1];

        int index = 0;
        for(LineIterator iterator = IOUtils.lineIterator(new FileReader("/Users/fredrik/temp/binaryCardsToHandMap_5_52.data")); iterator.hasNext() && index < readCount; index++) {
            String line = iterator.next();
            String[] tokens = line.split(",");
            long hand = Long.parseLong(tokens[0].trim(), 2);
            int value = Integer.parseInt(tokens[1].trim());
            inputArrays[index][0] = normalizeLong(hand);
            idealArrays[index][0] = normalizeInt(value);
            System.out.println("Input " + inputArrays[index][0] + " output " + idealArrays[index][0]);
        }

        // create training data
        MLDataSet trainingSet = new BasicNeuralDataSet(inputArrays, idealArrays);

        // train the neural network
        SVMTrain train = new SVMTrain(network, trainingSet);
        //final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
        //train.setThreadCount(0);

        int epoch = 1;

        do {
            train.iteration();
            if(epoch % 1000 == 0)
                System.out.println("Epoch #" + epoch + " Error: " + train.getError());
            epoch++;
        } while(train.getError() > 0.0000001);

        // test the neural network
        System.out.println("Neural Network Results:");
        for(MLDataPair pair: trainingSet ) {
            final MLData output = network.compute(pair.getInput());
            //int correct = deNormalizeInt(pair.getIdeal().getData(0));
            //int calculated = deNormalizeInt(output.getData(0));


            double correct = pair.getIdeal().getData(0);
            double calculated = output.getData(0);

            System.out.println("calculated=" + calculated + ", actual=" + correct + ", diff=" + (calculated-correct));
        }


    }

    public static double normalizeLong(long val) {
        return 1.0 / (val * 1.0);
    }

    public static double normalizeInt(int val) {
        return val / 100000000.0;
    }

    public static long deNormalizeLong(double val) {
        return (long)(1.0 / val);
    }

    public static int deNormalizeInt(double val) {
        return (int)val * 100000000;
    }

}
