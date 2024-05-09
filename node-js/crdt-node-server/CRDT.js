import { PriorityQueue } from 'priorityqueuejs';

class Node {
    constructor(position, letter, bold = false, italic = false, tombstone = false) {
        this.position = position;
        this.letter = letter;
        this.bold = bold;
        this.italic = italic;
        this.tombstone = tombstone;
        this.timestamp = Date.now();
    }
}

class CRDT {
    constructor() {
        this.nodes = new PriorityQueue();
        this.nodes.enqueue(new Node(Number.MIN_VALUE, ''));
        this.nodes.enqueue(new Node(Number.MAX_VALUE, ''));
    }

    insert(node) {
        this.nodes.enqueue(node);
    }

    delete(node) {
        let index = this.findPosition(node.position);
        if (index !== -1) {
            this.nodes.update(node, { tombstone: true });
        }
    }

    findPosition(position) {
        let index = -1;
        this.nodes.forEach((node, i) => {
            if (node.position > position) {
                index = i;
                return false; // Stop iterating
            }
        });
        return index;
    }

    cleanup() {
        this.nodes = this.nodes.filter(node => !node.tombstone);
    }

    indexToPosition(index) {
        let count = 0;
        let position = -1;
        this.nodes.forEach((node, i) => {
            if (!node.tombstone) {
                if (count === index) {
                    position = (this.nodes[i].position + this.nodes[i + 1].position) / 2;
                    return false; // Stop iterating
                }
                count++;
            }
        });
        return position;
    }

    positionToIndex(position) {
        let index = -1;
        let count = 0;
        this.nodes.forEach((node, i) => {
            if (!node.tombstone) {
                if (node.position === position) {
                    index = count;
                    return false; // Stop iterating
                }
                if (node.position < position) {
                    count++;
                }
            }
        });
        return index;
    }
}