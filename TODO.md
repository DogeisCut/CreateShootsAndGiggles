Lazily copy and pasted from: https://docs.google.com/document/d/1R0ciMEzMxvr2Q-aGn8icWw-xMD8lKACuqyXyms4tk1M/edit?tab=t.0

# Create: Shoots and Giggles

[Features](#features)

[Redstone Link Networker](#redstone-link-networker)

[Vapor Chamber](#vapor-chamber)

[Handheld Air Blower](#handheld-air-blower)

[Threshold Link](#threshold-link)

[LUA Computer](#lua-computer)

# Features

## Redstone Link Networker

An item/block that can connect redstone links (and any blocks that interact with redstone links, like the aeronautics typewriter) into a network. These networked links will not activate redstone links outside of its network. The network is tied to the item itself, so if the item is duplicated (such as spawning a schematic) so is the network, allowing multiple of the same build with the same links. The networker can also be placed and retains its behavior, it can also be assigned to a new slot inside the redstone link controller to make that controller use that network. If the Networker is destroyed, the entire network goes back to being normal links.

## Vapor Chamber

A new machine that requires super heating. It’s able to de-craft a couple resources by vaporizing and condensating them (usually into powder that needs to be re-melted into nuggets). Its recipes are of course data driven, but it can also try to auto generate some from a vaporizable resource. Things like wood or kelp in recipes don't get salvaged. Data can also define recipe or item blacklists  
Examples:

* brass \-\> copper, zinc
* Andesite alloy \-\> andesite, iron
* Brass funnel \-\> copper, zinc, redstone, quartz, iron

## Handheld Air Blower

uses backtank air to blow entities and sublevels around when right click is held. Crouching while using it charges up a meter, releasing it in the green area launches a wind charge, and letting it overfill explodes a wind charge in your face. The airblower also has recoil, but its only super noticeable if you blow air below you where you can almost fly, reducing gravity’s effect on you with a counter force.

## Threshold Link

A logistics-network block that watches one item's stock (optionally including  
promised/in-transit amounts) and emits a redstone signal once it crosses a  
threshold. Feed that signal into a farm's control circuit and a Packager's/  
Frogport's redstone input to stop production and shipping at the same moment  
your network has "enough."

## LUA Computer

Uses lua code to control its rotational, redstone, and comparator output, based on input or on right click. Requires a wrench to properly edit.

## Dyed Ice

Figured I should port these from that one unfinished mod I had. The only reason it’s here is for Sable sublevels and tag support. Could also add support for right click dying too.  
Ideally these would have their own creative inventory tab as to not mess up the main one

## Bomb

Throws out as an entity like how packages do it. Looks like a cartoon bomb and explodes after a bit.

## Steering Wheel

Is actually a full block, has the wheel sticking out the front (or alternatively can be angled into the block via a wrench), and a shaft input at the bottom, and output on the back. The steering wheel takes in rotational power, and the player can drag and turn the wheel by holding right click, the shaft on the back will turn the direction of the wheel, and speed up closer to the power at the bottom the closer the wheel gets to reaching its maximum turn radius. The turn radius can be customized via a menu on the side of the block.