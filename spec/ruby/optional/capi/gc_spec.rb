require_relative 'spec_helper'

load_extension("gc")

describe "CApiGCSpecs" do
  before :each do
    @f = CApiGCSpecs.new
  end

  it "correctly gets the value from a registered address" do
    @f.registered_tagged_address.should == 10
    @f.registered_tagged_address.should equal(@f.registered_tagged_address)
    @f.registered_reference_address.should == "Globally registered data"
    @f.registered_reference_address.should equal(@f.registered_reference_address)
  end

  describe "rb_gc_enable" do

    after do
      GC.enable
    end

    it "enables GC when disabled" do
      GC.disable
      @f.rb_gc_enable.should be_true
    end

    it "GC stays enabled when enabled" do
      GC.enable
      @f.rb_gc_enable.should be_false
    end

    it "disables GC when enabled" do
      GC.enable
      @f.rb_gc_disable.should be_false
    end

    it "GC stays disabled when disabled" do
      GC.disable
      @f.rb_gc_disable.should be_true
    end
  end

  describe "rb_gc" do

    it "increases gc count" do
      gc_count = GC.count
      @f.rb_gc
      GC.count.should > gc_count
    end

  end

  describe "rb_gc_register_mark_object" do
    it "can be called with an object" do
      @f.rb_gc_register_mark_object(Object.new).should be_nil
    end
  end
end
