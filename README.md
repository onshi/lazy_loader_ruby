lazy_loader
========

[![Build Status](https://travis-ci.org/peter-edge/lazy_loader_ruby.png?branch=master)](https://travis-ci.org/peter-edge/lazy_loader_ruby)

Lazy loading for MRI Ruby and JRuby.

Uses double-locking and a volatile variable if in JRuby, and uses ||= in MRI Ruby.

## Usage

```ruby
require 'lazy_loader'
require 'thread'        # for Queue

i = 0
foo = LazyLoader.create_lazy_loader do
  i += 1
  i
end

queue = Queue.new
(1..100).map do |_|
  Thread.new do
    queue << foo.get    # always 1
  end
end.each do |thread|
  thread.join
end

sum = 0
sum += queue.pop until queue.empty?
puts sum                # 100
```

Written for Locality  
http://www.locality.com

## Authors

Peter Edge  
peter@locality.com  
http://github.com/peter-edge

## License

MIT
