package train;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Bayes {
    
      public static class TokenizerMapper 
           extends Mapper<Object, Text, Text, IntWritable>{
        
        private final static IntWritable ids = new IntWritable();
        private Text word = new Text();
        
//        private Text ids = new Text();
          
        public void map(Object key, Text value, Context context
                        ) throws IOException, InterruptedException {
        	String line = value.toString();
        	String [] str = line.split(" -\\+- ");
            byte[] bt = str[1].getBytes();
            InputStream ip = new ByteArrayInputStream(bt);
            Reader read = new InputStreamReader(ip);
            IKSegmenter iks = new IKSegmenter(read,true);
            Lexeme t;
            while ((t = iks.next()) != null)
            {
                word.set(t.getLexemeText()+"_"+str[2]);
                ids.set(Integer.parseInt(str[0]));
                context.write(word, ids);
            }
        }
      }
  
  public static class IntSumReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
//    private IntWritable result = new IntWritable();
//	  private Text rese = new Text();
    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
    	Set<Integer> set = new HashSet<Integer>();
    	for(IntWritable val : values){
    		set.add(val.get());
    	}
    	String [] res = key.toString().split("_");
    	String re = res[0]+"	"+res[1];
    	
    	context.write(new Text(re), new IntWritable(set.size()));
//      int sum = 0;
//      for (IntWritable val : values) {
//        sum += val.get();
//      }
//      result.set(sum);
//      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: Bayes <in> <out>");
      System.exit(2);
    }
    Job job = new Job(conf, "Bayes");
    job.setJarByClass(Bayes.class);
    job.addArchiveToClassPath(new Path("IKAnalyzer2012_u6.jar"));
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
//    job.setMapOutputKeyClass(Text.class);
//    job.setMapOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}