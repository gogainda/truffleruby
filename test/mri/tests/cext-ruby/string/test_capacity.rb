# frozen_string_literal: true
require 'test/unit'
require 'c/string'
require 'rbconfig/sizeof'

class Test_StringCapacity < Test::Unit::TestCase
  def capa(str)
    Bug::String.capacity(str)
  end

  def test_capacity_embedded
    size = RbConfig::SIZEOF['void*'] * 3 - 1
    assert_equal size, capa('foo')
  end

  def test_capacity_shared
    assert_equal 0, capa(:abcdefghijklmnopqrstuvwxyz.to_s)
  end

  def test_capacity_normal
    assert_equal 128, capa('1'*128)
  end

  def test_s_new_capacity
    assert_equal("", String.new(capacity: 1000))
    assert_equal(String, String.new(capacity: 1000).class)
    assert_equal(10000, capa(String.new(capacity: 10000)))

    assert_equal("", String.new(capacity: -1000))
    assert_equal(capa(String.new(capacity: -10000)), capa(String.new(capacity: -1000)))
  end

  def test_io_read
    s = String.new(capacity: 1000)
    open(__FILE__) {|f|f.read(1024*1024, s)}
    assert_equal(1024*1024, capa(s))
    open(__FILE__) {|f|s = f.read(1024*1024)}
    assert_operator(capa(s), :<=, s.bytesize+4096)
  end

  def test_literal_capacity
    s = "I am testing string literal capacity"
    assert_equal(s.length, capa(s))
  end

  def test_capacity_frozen
    s = String.new("I am testing", capacity: 1000)
    s << "fstring capacity"
    s.freeze
    assert_equal(s.length, capa(s))
  end

  def test_capacity_fstring
    s = String.new("I am testing", capacity: 1000)
    s << "fstring capacity"
    s = -s
    assert_equal(s.length, capa(s))
  end
end
