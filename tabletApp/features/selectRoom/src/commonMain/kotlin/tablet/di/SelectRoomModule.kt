package tablet.di

import org.koin.dsl.module
import tablet.domain.ISelectRoomInteractor
import tablet.domain.SelectRoomInteractorImpl

val selectRoomModule = module{
    single<ISelectRoomInteractor> { SelectRoomInteractorImpl(get()) }
}
