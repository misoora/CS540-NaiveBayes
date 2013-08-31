CS540-NaiveBayes
================

This program is a Naive Bayes classifier that classifies english, spanish, and japanese documents.

It uses training sets to determine pre-probabilities to use in the algorithm such as "what's the probablility of 
the letter 'a' showing up in an english document". After that it uses all the probabilities on the testing set to 
test the accuracy of the classifier. 

*The NaiveBayes.java file contains the main class and all the source code.
*trainingdataset and testdataset contain .txt files with english, spanish, and japanese documents.

To actually run the program you can use the NaiveBayes.jar file.
  *On Windows navigate to the directory and run: java -jar NaiveBayes.jar trainingdataset testdataset
  
More Information on Naive Bayes here: http://en.wikipedia.org/wiki/Naive_Bayes_classifier 
