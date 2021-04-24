# forgettingmap
Implementation of a Forgetting Map

## Implementation Notes
- The chosen implementation uses an eviction policy of least used - i.e. the content with the least number of 'hits' will be evicted when the map reaches capacity.
- Tie-breakers are settled using a policy of last accessed, i.e. content that was accessed least recently will be evicted for where there are multiple content associations that have the same number of 'hits'.

## Outstanding/future tasks
- Build/publish configuration (to make available as a jar file)
- Performance
  - Thread safety has been implemented using method-level synchronization. This favours thread security, since it ensures that both read and write operations are safe, but synchronization of the entire object is not performant. Further investigation of a way to retain thread safety would involve identifying necessarily atomic processes and applying synchronization at this lower level.
  - Each time the map reaches capacity, it needs sorting to determine which item should be evicted. While this uses Java List sorting, with an expected time complexity of O(n log n), it is possible that this could be refined if performance were really an issue.
- Testing
  - Basic, functional scenarios have been covered, but more detailed edge tests would ideally be added.
  - Some kind of performance test would also be useful, possibly including scenarios that would identify issues of thread safety, but the latter kind of tests are often complext to implement and rarely deterministic.
- Refactor
  - The code is not easily extended as is, so some refactoring to, say a builder pattern for the underlying map, would allow easier implementation of different eviction policies.
