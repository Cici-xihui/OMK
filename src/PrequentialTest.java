import com.yahoo.labs.samoa.instances.Prediction;
import com.yahoo.labs.samoa.instances.*;
import moa.classifiers.MultiLabelLearner;
import moa.core.InstanceExample;
import moa.core.SizeOf;
import moa.core.TimingUtils;
import moa.evaluation.BasicMultiLabelPerformanceEvaluator;
import moa.streams.ConceptDriftStream;
import moa.streams.MultiTargetArffFileStream;
import moa.streams.generators.RandomRBFGenerator;
import moa.streams.generators.cd.GradualChangeGenerator;

import java.io.IOException;

public class PrequentialTest {

    private int totalInstances;

    public PrequentialTest(int amountInstances) {

        if (amountInstances == -1) {
            this.totalInstances = 2147483647;
        } else {
            this.totalInstances = amountInstances;
        }
    }

    public void run() throws IOException {

        // preparation
        //MultiTargetArffFileStream stream = new MultiTargetArffFileStream(".././MLS_Datasets/20NG/20NG-F.arff", "20");
        //MultiTargetArffFileStream stream = new MultiTargetArffFileStream(".././MLS_Datasets/Bookmarks/bookmarks.arff", "-208");
        //MultiTargetArffFileStream stream = new MultiTargetArffFileStream(".././MLS_Datasets/Corel16k/Corel16k001.arff", "-153");
        //MultiTargetArffFileStream stream = new MultiTargetArffFileStream(".././MLS_Datasets/Enron.arff", "53");
        //MultiTargetArffFileStream stream = new MultiTargetArffFileStream(".././MLS_Datasets/Mediamill/mediamill.arff", "-101");
        //MultiTargetArffFileStream stream = new MultiTargetArffFileStream(".././MLS_Datasets/Ohsumed/OHSUMED-F.arff", "23");
        //MultiTargetArffFileStream stream = new MultiTargetArffFileStream(".././MLS_Datasets/tmc2007-500/tmc2007-500.arff", "22");
        //MultiTargetArffFileStream stream = new MultiTargetArffFileStream(".././MLS_Datasets/Yeast.arff", "14");


        MetaMultilabelGenerator mlstream = new MetaMultilabelGenerator();
        mlstream.binaryGeneratorOption.setValueViaCLIString("generators.RandomRBFGenerator");
        mlstream.labelCardinalityOption.setValue(2.8);
        mlstream.numLabelsOption.setValue(10);
        MetaMultilabelGenerator cdstream = new MetaMultilabelGenerator();
        cdstream.binaryGeneratorOption.setValueViaCLIString("generators.RandomRBFGenerator");
        cdstream.labelCardinalityOption.setValue(2.8);
        cdstream.labelDependencyChangeRatioOption.setValue(1);
        cdstream.numLabelsOption.setValue(10);
        ConceptDriftStream stream = new ConceptDriftStream();
        stream.streamOption.setCurrentObject(mlstream);
        stream.driftstreamOption.setCurrentObject(cdstream);






        stream.prepareForUse();



        OMK learner = new OMK();


        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();

        PrequentialMultiLabelPerformanceEvaluator evaluator = new PrequentialMultiLabelPerformanceEvaluator();

        // Online process
        long starttime = TimingUtils.getNanoCPUTimeOfCurrentThread();
        int numberInstances = 0;
        while(numberInstances < totalInstances && stream.hasMoreInstances()) {

            InstanceExample trainInst = (InstanceExample) stream.nextInstance();
            Prediction prediction = learner.getPredictionForInstance(trainInst);
            evaluator.addResult(trainInst, prediction);
            learner.trainOnInstance(trainInst);

            ++numberInstances;;

        }


        System.out.println(numberInstances + " instances.");
        long endtime = TimingUtils.getNanoCPUTimeOfCurrentThread();

        for(int i = 0; i < evaluator.getPerformanceMeasurements().length; i++)
            System.out.println(evaluator.getPerformanceMeasurements()[i].getName() + "\t" + String.format("%.3f",evaluator.getPerformanceMeasurements()[i].getValue()));

        String timeString = "Time: " + TimingUtils.nanoTimeToSeconds((endtime - starttime)) + " s  \n";
        System.out.println(timeString + "\n");



        //BaseLine.executeMethod("OMK", "bookmarks",learner, starttime, evaluator);
        //System.out.println("Size of the model: " + SizeOf.fullSizeOf(learner) + " bytes\n");


    }

    public static void main(String[] args) throws IOException {
       PrequentialTest batch = new PrequentialTest(100000);
        batch.run();
    }

}







