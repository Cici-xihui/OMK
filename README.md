# Exploration des mémoires pour la classification multi-labels en flux

While Multi-label classification has been widely studied, few works considered the Multi-label streaming problem. To address this issue, we propose a new algorithm: Online Memory k-Means (OMk). OMk is based on a K-nearest neighbors' strategy which integrates two types of memories with limited size: a short-term and a long-term memory. The short-term memory is a FIFO window containing only the recent concept while the long-term memory, embedding previous concepts, relies on a tree-structure using a reservoir sampling strategy. The designed memories allow us to manage streams with concept drifts and resist to catastrophic forgetting. The experimental study compares the proposal to the current state-of-the-art algorithms using seven real-world and four artificial multi-label data streams on three multi-label metrics and evaluation time. OMk shows competitiveness and low time complexity, in both stationary and concept drift scenarios.

All experiments were implemented in MOA : https://moa.cms.waikato.ac.nz/ with jvm -v 11.0.4

## Experimental data
### Stationnaire stream data :
- 20NG
- Bookmarks
- Corel16k
- Enron
- Mediamill
- Ohsumed
- Tmc2007-500
All data can be found on : http://www.uco.es/kdis/mllresources/

### Non-stationnaire stream data:
We have selected two generators (Random RBF and RandomTree) from the MOA environment and adapted them to generate multi-label data. 

> <cite> Xihui WANG, Pascale KUNTZ et Frank MEYER </cite>
