# OMk

While Multi-label classification has been widely studied, few works considered the Multi-label streaming problem. To address this issue, we propose a new algorithm: Online Memory k-Means (OMk). OMk is based on a K-nearest neighbors' strategy which integrates two types of memories with limited size: a short-term and a long-term memory. The short-term memory is a FIFO window containing only the recent concept while the long-term memory, embedding previous concepts, relies on a tree-structure using a reservoir sampling strategy. The designed memories allow us to manage streams with concept drifts and resist to catastrophic forgetting. The experimental study compares the proposal to the current state-of-the-art algorithms using seven real-world and four artificial multi-label data streams on three multi-label metrics and evaluation time. OMk shows competitiveness and low time complexity, in both stationary and concept drift scenarios.


Experimental data can be found on : http://www.uco.es/kdis/mllresources/
