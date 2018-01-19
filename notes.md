# Things I learned

* Bias is the amount that a neural network reflects the noise in its data
* Bias can be measured by comparing how much the result changes based on using different subsets of training data
* Variance is the opposite problem of bias
* Minimizing the sum of bias and variance is a good method to minimize total error of the net
* Stochastic backpropagation usually
  * results in better solutions
  * faster *> less wasteful, esp. on redundant looking training data
  * can be used to track changes?
  
* when training
  * shuffle training so successive training examples don't belong to same class
  * present training inputs that produce large errors more frequently than samples that produce small errors (beat the trainer up)


# Nets

* Relu (property of a layer?)- type of layer that ...?
* Dropout layer - reduces the connectivity between two layers (so they are no longer fully connected nodes) to prevent over-fitting
  * One property is it makes it so the net has to find multiple ways to get the same answer 
