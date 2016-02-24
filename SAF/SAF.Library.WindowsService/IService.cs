namespace SAF.Library.WindowsService
{
	/// <summary>
	/// IService interface represents the interface class has
	/// to implement to be pluggable into SAF.WindowsService
	/// </summary>
	public interface IService
	{
		void Start();
		void Stop();
		void Initialize();
		Service ServiceInfo { get; }
	}
}
