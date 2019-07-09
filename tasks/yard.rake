require 'yard'

YARD::Rake::YardocTask.new(:doc) do |t|
  version = LazyLoader::GEM_VERSION
  t.options = ["--title", "LazyLoader #{version}", "--files", "LICENSE"]
end
