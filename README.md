# SpigotUtils
A work in progress library containing code that I find  useful when creating plugins. 
SpigotUtils is split into two parts: API and Plugin. The API is standalone and can be 
used independently. The plugin will build upon the API providing features such as auto-reloading
yml configurations

### Motivation
When developing plugins, project setup can take more time than intended. By creating a 
library containing classes/methods I find myself implementing frequently I can expedite
this process tremendously. 

### Current Features
- Location Adapter (Serialize/deserialize location objects)
- Builder classes (ItemStack and PotionEffect)
- AbstractConfig (Easily generate plugin folders/yml configurations and cache data)
- Constants (Enchant, Entity, and Material constants)
- Reward & RewardList (Weighted objects with a supporting collection)
- Miscellaneous Utils (File, Hashing, ItemStack, Location, Player, Text)

### Planned Features
- Mob Builder/Custom Mob (API)
- Area class/adapter (API)
- Auto-Reloading YML Configurations (Plugin)
- Custom Item API (Plugin)

### References
- [Comparison between JSON and YAML for data serialization](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.1048.2508&rep=rep1&type=pdf)
- ["Effective Java Second Edition" by Joshua Bloch](https://www.amazon.com/Effective-Java-2nd-Joshua-Bloch/dp/0321356683)